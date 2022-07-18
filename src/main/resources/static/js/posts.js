var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });
        $('#btn-category-add').on('click', function () {
            _this.category_add();
        });
        $('#btn-edit').on('click', function () {
            _this.update();
        });
        $('#btn-remove').on('click', function () {
            _this.remove();
        });
        $('#edit-category-id').on('change', function () {
            _this.category_index_change();
        });
        $('#btn-category-edit').on('click', function () {
            _this.category_edit();
        });
        $('#btn-category-remove').on('click', function () {
            _this.category_remove();
        });
    },
    save : function () {
        var data = {
            category_id: $('#category').val(),
            content: simplemde.value(),
            title: $('#title').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/v1/posts",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/posts/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    category_add : function () {
        var data = {
            category_name: $('#category-title').val(),
            index: $('#category-index').val()
        };

        $.ajax({
            type: 'POST',
            url: "/api/v1/category",
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            $('#ModalForm').modal('hide');
            alert('카테고리가 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
            location.reload();
        });
    },
    update : function () {
        var data = {
            category_id: $('#category').val(),
            content: simplemde.value(),
            title: $('#title').val()
        };
        $.ajax({
            type: 'PUT',
            url: "/api/v1/posts/"+document.getElementById('post_id').value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('수정이 완료되었습니다!');
            window.location.href = '/posts/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    remove : function () {
        $.ajax({
            type: 'DELETE',
            url: "/api/v1/posts/"+document.getElementById('post_id').value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            $('#RemoveModalForm').modal('hide');
            alert('삭제 완료!');
            window.location.href = '/posts/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    category_index_change : function (){
        var target = document.getElementById("edit-category-id");

        $.ajax({
            type: 'GET',
            url: "/api/v1/category/"+document.getElementById("edit-category-id").value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(data) {
            document.getElementById("edit-category-name").innerText = target.options[target.selectedIndex].text;
            document.getElementById("edit-category-index").innerText = data.index;
        }).fail(function (error) {
            document.getElementById("edit-category-index").innerText = ""
        });
    },
    category_edit : function () {
        var data = {
            category_name: $('#edit-category-name').val(),
            index: $('#edit-category-index').val()
        };

        $.ajax({
            type: 'PUT',
            url: "/api/v1/category/"+document.getElementById("edit-category-id").value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            $('#EditModalForm').modal('hide');
            alert('카테고리가 편집되었습니다!');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
            location.reload();
        });
    },
    category_remove : function () {

        $.ajax({
            type: 'DELETE',
            url: "/api/v1/category/"+document.getElementById("edit-category-id").value,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function() {
            $('#EditModalForm').modal('hide');
            alert('카테고리가 삭제되었습니다!');
            location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
            location.reload();
        });
    }
};

main.init();

var simplemde = new SimpleMDE({ 	autosave: {
        enabled: true,
        uniqueId: window.location.pathname,
        delay: 1000,
    },
    renderingConfig: {
        codeSyntaxHighlighting: true,
    },
    spellChecker: false,
    element: document.getElementById("content"),
});
simplemde.value(document.getElementById('post_content').value);

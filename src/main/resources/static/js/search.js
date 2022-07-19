var search = {
    init : function () {
        var _this = this;
        $('#btn-search').on('click', function () {
            _this.search();
        });
    },
    search : function () {
        if(document.getElementById("input-search").value === ""){
            alert("검색할 내용을 입력해주세요!");
        }
        else if(document.getElementById("input-search").value.length === 1){
            alert("검색어를 1글자 이상 입력해주세요!");
        }
        else{
            window.location.href = '/posts/search?page=1&content='+document.getElementById("input-search").value;
        }


    }
};

search.init();

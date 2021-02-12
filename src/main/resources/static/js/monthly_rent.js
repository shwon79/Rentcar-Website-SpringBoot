function changes(fr, detailedSelect) {
    if(fr=="rentMonth") {
        //뿌려줄값을 배열로정렬
        num = new Array("국산 승용","국산승합•RV","수입");
        vnum = new Array("1","2","3");
    } else if(fr=="rentYear") {
        num = new Array("2)첫번째목록","2)두번째목록","2)세번째목록");
        vnum = new Array("1","2","3");
    }
    // 셀렉트안의 리스트를 기본값으로 한다..
    for(i=0; i<detailedSelect.length; i++) {
        detailedSelect.options[0] = null;
    }
    //포문을 이용하여 두번째(test2)셀렉트 박스에 값을 뿌려줍니당)
    for(i=0;i < num.length;i++) {
        detailedSelect.options[i] = new Option(num[i],vnum[i]);
    }
}
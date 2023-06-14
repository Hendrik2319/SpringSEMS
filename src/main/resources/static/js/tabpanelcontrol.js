let TabPanelControl = (function(){

    let selected = null;
    
    function setElement( elementID, isSelected ) {
        let element = document.getElementById(elementID);
        if (!element) return;

        if (isSelected)
            element.classList.add('SelectedTab');
        else
            element.classList.remove('SelectedTab');
    }

    function showSelection( elements, isSelected ) {
        if (elements) {
            setElement( elements.buttonID , isSelected );
            setElement( elements.contentID, isSelected );
        }
    }

    function select( tabBtnID, tabContID ) {
        showSelection( selected, false );
        selected = { buttonID:tabBtnID, contentID:tabContID };
        showSelection( selected, true );
    }

    return {
        select,
    }
})();
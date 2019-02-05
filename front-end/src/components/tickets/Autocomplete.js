var options = {
    url: "airports.json",

    getValue: "name",

    list: {
        match: {
            enabled: true
        },
        onClickEvent: function() {
            alert($("#provider-json").getSelectedItemData().code);
        },
        onKeyEnterEvent: function () {
            alert($("#provider-json").getSelectedItemData().code)
        }
    }
};

$("#provider-json").easyAutocomplete(options);
var deleteData = [];
Ext.onDocumentReady(function () {
  var states = Ext.create("Ext.data.Store", {
    fields: ["abbr", "language"],
    data: [
      { abbr: "EN", language: "English" },
      { abbr: "HN", language: "Hindi" },
      { abbr: "BH", language: "Bhojpuri" },
    ],
  });
  Ext.create("Ext.panel.Panel", {
    renderTo: Ext.getBody(),

    height: 200,
    width: "1280px",
    title: "<b> Movie advance search</b>",
    layout: "absolute",
    header: {
      style: {
        backgroundColor: "light blue",
      },
    },

    items: [
      {
        xtype: "textfield",
        fieldLabel: "<b>Movie name</b>",
        x: 300,
        y: 20,
        height: 25,
        width: "20%",
        columnWidth: 0.5,

        defaults: {
          flex: 0.25,
        },
        margin: "10 10 0 10",
      },

      {
        xtype: "datefield",
        fieldLabel: "<b>Release date</b>",
        x: 650,
        y: 20,
        height: 25,
        width: "20%",
        columnWidth: 0.5,

        defaults: {
          flex: 0.25,
        },
        margin: "10 10 0 10",
      },
      {
        xtype: "textfield",
        fieldLabel: "<b>Director</b>",
        x: 300,
        y: 70,
        height: 25,
        width: "20%",
        columnWidth: 0.5,

        defaults: {
          flex: 0.5,
        },
        margin: "10 10 0 10",
      },

      {
        xtype: "combobox",
        fieldLabel: "<b>language</b>",
        x: 650,
        y: 70,
        height: 25,
        width: "20%",
        columnWidth: 0.5,
        store: states,
        queryMode: "local",
        displayField: "language",
        valueField: "abbr",
        defaults: {
          flex: 0.25,
        },
        margin: "10 10 0 10",
      },
    ],
  });

  // Ext.create('Ext.panel.Panel', {
  //    renderTo: Ext.getBody(),
  //    height: 100,
  //    layout:'absolute',
  //   items: [

  //   ],
  //});
  Ext.create("Ext.Container", {
    renderTo: Ext.getBody(),

    style: {
      backgroundColor: "grey",
    },

    layout: {
      type: "hbox",
    },
    items: [
      {
        xtype: "button",
        text: "<b>Search</b>",
        width: 100,
        margin: "5 20 10 500",
        padding: 5,
        style: {
          backgroundColor: "light blue",
        },
      },
      {
        xtype: "button",
        text: "<b>Reset</b>",
        width: 100,
        margin: "5 20 10 0",
        padding: 5,

        style: {
          backgroundColor: "light blue",
        },
      },
    ],
  });
  var itemsPerPage = 10;
  var store = new Ext.data.Store({
    storeId: "invoiceStore",
    autoLoad: false,
    fields: ["Title", "Description", "Release", "Language", "Director", "Rating", "Features"],
    pageSize: itemsPerPage,
    proxy: {
      type: "ajax",
      // actionMethods: {
      // 	read: 'POST'
      // },
      url: "http://localhost:8080/MovieApp_war_exploded/films",
      reader: {
        type: "json",
        rootProperty: "films",
        totalProperty: "total",
      },
    },

  });
  store.load({
    params: {
      start: 0,
      limit: itemsPerPage,
    },
  });
  store.reload();

  Ext.create("Ext.grid.Panel", {
    title: "<b>Movie Grid</b>",
    store: store,
    id: "testGrid",
    dockedItems: [
      {
        xtype: "pagingtoolbar",
        store: store, // same store GridPanel is using
        dock: "top",
        displayInfo: true,
        inputItemWidth: "5%",
        //  jsonSubmit :true ,
        //  writeAllFields : true
        renderTo: Ext.getElementById("Add1"),
        items: [
          ,
          "-",
          {
            xtype: "button",
            textAlign: "center",
            text: '<span class="x-fa fa-plus-circle fa-lg" style="color:#42a1f5;"></span>  Add',
            id: "Add1",
            listeners: {
              click: function () {
                win.show();
              },
            },
          },
          "-",
          {
            xtype: "button",

            textAlign: "center",
            text: '<span class="x-fa fa-edit fa-lg" style="color:#42a1f5;"></span>  Edit',
            disabled: false,
            id: "editButton",
            listeners: {
              click: function () {
                Edi.show();
                var data = Ext.getCmp("testGrid").getSelectionModel().getSelection()[0].data;
                selectedrows.push([
                  {
                    film_id: data.film_id,
                    director: data.director,
                    description: data.description,
                    title: data.title,
                    release: data.release_year,
                    features: data.special_features,
                    rating: data.rating,
                    language: data.language,
                  },
                ]);
                editStore.setData(selectedrows);
                console.log(selectedrows);
                console.log(editStore.getData().items[selectedrows.length - 1].data.title.film_id);
                Ext.getCmp("director").setValue(selectedrows[selectedrows.length - 1][0].director);
                Ext.getCmp("des").setValue(selectedrows[selectedrows.length - 1][0].description);
                Ext.getCmp("title").setValue(selectedrows[selectedrows.length - 1][0].title);
                Ext.getCmp("rYear").setValue(selectedrows[selectedrows.length - 1][0].release);
                Ext.getCmp("sfeatures").setValue(selectedrows[selectedrows.length - 1][0].features);
                Ext.getCmp("rating").setValue(selectedrows[selectedrows.length - 1][0].rating);
                Ext.getCmp("language").setValue(selectedrows[selectedrows.length - 1][0].language);
              },
            },
            //handler: {
            //	 function(){

            //	 }
            //  },
          },
          "-",
          {
            xtype: "button",
            text: '<span class="x-fa fa-trash fa-lg" style="color:#42a1f5;"></span> Delete',
            textAlign: "center",
            id: "deleteButton",

            disabled: false,
            listeners: {
              click: function () {
                Del.show();
                var data = Ext.getCmp("testGrid").getSelectionModel().getSelection()[0].data;
                selectedrows.push([
                  {
                    film_id: data.film_id,
                  },
                ]);
                /*   deleteStore.setData(selectedrows);
                console.log(selectedrows);
                console.log(
                  deleteStore.getData().items[selectedrows.length - 1].data
                    .film_id
                );*/
                Ext.getCmp("testGrid")
                  .getSelectionModel()
                  .getSelection()
                  .map((dat) => {
                    deleteData.push(dat.data.film_id);
                  });
                console.log(deleteData);
              },
            },
          },
        ],
      },
    ],
    columns: [
      {
        text: "Title",
        dataIndex: "title",
      },
      {
        text: "Description",
        dataIndex: "description",
        flex: 1,
      },
      {
        text: "Release Year",
        dataIndex: "release_year",
      },
      {
        text: "Language",
        dataIndex: "language",
      },
      {
        text: "Director",
        dataIndex: "director",
      },
      {
        text: "Rating",
        dataIndex: "rating",
      },
      {
        text: "Features",
        dataIndex: "special_features",
      },
      {
        hidden: true,
        text: "Film_id",
        dataIndex: "film_id",
      },
    ],

    height: 340,
    width: "1280px",

    renderTo: Ext.getBody(),
    selModel: {
      checkOnly: false,
      injectCheckbox: "first",
      mode: "SIMPLE",
      selType: "checkboxmodel",
      id: "checkBox",
    },
  });

  //Ext.create('Ext.Container', {
  //	    renderTo: Ext.getBody(),
  //        width: '100%',
  //	    style: {

  //           backgroundColor: 'grey'
  //
  //     },

  //  layout: {
  //      type: 'hbox'
  //   },
  // });
});
win = Ext.create("Ext.window.Window", {
  title: "Add Film",
  height: 450,
  width: 400,
  closeAction: "hide",
  target: document.getElementById("Add1"),
  layout: "fit",
  items: {
    xtype: "form",
    id: "myForm",
    url: "http://localhost:8080/MovieApp_war_exploded/adddata",

    border: false,
    closeAction: "close",
    plain: true,
    layout: "vbox",
    items: [
      {
        xtype: "textfield",
        fieldLabel: "Title",
        margin: "10 0 0 10",
        name: "title",
      },
      {
        xtype: "numberfield",
        fieldLabel: "Release Year",
        margin: "10 0 0 10",
        maxValue: 2021,
        minValue: 1990,
        name: "release_year",
      },
      {
        xtype: "combobox",
        fieldLabel: "special features",
        margin: "10 0 0 10",
        store: Ext.create("Ext.data.Store", {
          fields: ["abbr", "name"],
          data: [
            {
              abbr: "Trailers",
              name: "Trailers",
            },
            {
              abbr: "Deleted Scenes",
              name: "Deleted Scenes",
            },
            {
              abbr: "Behind The Scenes",
              name: "Behind The Scenes",
            },
          ],
        }),
        valueField: "abbr",
        displayField: "name",
        name: "special_features",
      },
      {
        xtype: "combobox",
        fieldLabel: "Rating",
        margin: "10 0 0 10",
        store: Ext.create("Ext.data.Store", {
          fields: ["abbr", "name"],
          data: [
            {
              abbr: "G",
              name: "G",
            },
            {
              abbr: "PG",
              name: "PG",
            },
            {
              abbr: "PG-13",
              name: "PG-13",
            },
            {
              abbr: "NC-17",
              name: "NC-17",
            },
            {
              abbr: "R",
              name: "R",
            },
          ],
        }),
        valueField: "abbr",
        displayField: "name",
        name: "rating",
      },
      {
        xtype: "combobox",
        fieldLabel: "language",
        margin: "10 0 0 10",
        store: Ext.create("Ext.data.Store", {
          fields: ["abbr", "name"],
          data: [
            {
              abbr: "1",
              name: "1 : Hindi",
            },
            {
              abbr: "2",
              name: "2 : English",
            },
            {
              abbr: "3",
              name: "3 : Anime",
            },
          ],
        }),
        valueField: "abbr",
        displayField: "name",
        name: "language",
      },
      {
        xtype: "textfield",
        fieldLabel: "Director",
        margin: "10 0 0 10",
        name: "director",
      },
      {
        xtype: "textarea",
        fieldLabel: "Description",
        margin: "10 0 0 10",
        name: "description",
      },
    ],
    buttons: [
      {
        text: "Save",
        margin: "0 20 0 0",

        handler: function () {
          var form = this.up("form").getForm();
          // console.log(form);
          if (form.isValid()) {
            form.submit({
              success: function (form, action) {
                Ext.Msg.alert("Success!", action.response.status);
                win.hide();
              },

              failure: function (form, action) {
                Ext.Msg.alert("Failed", "Please try again!", action.response.status);
                console.log(action.response.status);
                win.hide();
              },
            });
          }
        },
      },
    ],
    store: Ext.create("Ext.data.ArrayStore", {}),
  },
});

var selectedrows = [];

let editStore = Ext.create("Ext.data.Store", {
  fields: ["title", "tescription", "release", "film_id", "director", "dating", "features"],
  storeId: "editStore",
  data: selectedrows,
});

let Edi = Ext.create("Ext.window.Window", {
  title: "Edit Row",
  height: 450,
  width: 360,
  closeAction: "hide",
  target: document.getElementById("editButton"),
  layout: "fit",
  items: {
    xtype: "form",
    id: "Form",
    url: "http://localhost:8080/MovieApp_war_exploded/Editdata",
    border: false,
    closeAction: "hide",
    plain: true,
    layout: "vbox",
    items: [
      {
        id: "title",
        xtype: "textfield",
        fieldLabel: "Title",
        margin: "10 0 0 10",
        name: "title",
      },
      {
        id: "language",
        xtype: "numberfield",
        fieldLabel: "Language ID",
        margin: "10 0 0 10",
        maxValue: 1,
        minValue: 0,
        name: "language",
      },
      {
        id: "rYear",
        xtype: "numberfield",
        fieldLabel: "Release Year",
        margin: "10 0 0 10",
        maxValue: 2021,
        minValue: 1990,
        name: "release_year",
      },
      {
        id: "sfeatures",
        xtype: "combobox",
        fieldLabel: "special features",
        margin: "10 0 0 10",
        store: Ext.create("Ext.data.Store", {
          fields: ["abbr", "name"],
          data: [
            {
              abbr: "Trailers",
              name: "Trailers",
            },
            {
              abbr: "Deleted Scenes",
              name: "Deleted Scenes",
            },
            {
              abbr: "Behind The Scenes",
              name: "Behind The Scenes",
            },
          ],
        }),
        valueField: "abbr",
        displayField: "name",
        name: "special_features",
      },
      {
        id: "rating",
        xtype: "combobox",
        fieldLabel: "Rating",
        margin: "10 0 0 10",
        store: Ext.create("Ext.data.Store", {
          fields: ["abbr", "name"],
          data: [
            {
              abbr: "R",
              name: "R",
            },
            {
              abbr: "PG",
              name: "PG",
            },
            {
              abbr: "C",
              name: "C",
            },
            {
              abbr: "NC-17",
              name: "PG",
            },
            {
              abbr: "PG-13",
              name: "PG-13",
            },
          ],
        }),
        valueField: "abbr",
        displayField: "name",
        name: "rating",
      },

      {
        id: "director",
        xtype: "textfield",
        fieldLabel: "Director",
        margin: "10 0 0 10",
        name: "director",
      },
      {
        id: "des",
        xtype: "textarea",
        fieldLabel: "Description",
        margin: "10 0 0 10",
        name: "description",
      },
    ],
    buttons: [
      {
        text: "Edit",
        formBind: true, //only enabled once the form is valid
        //   disabled: true,
        handler: function () {
          var form = this.up("form").getForm();
          var formData = this.up("form").getForm().getValues();
          var editStoreData = editStore.getData();
          console.log(editStoreData);
          console.log(selectedrows[0][0].film_id);
          console.log(selectedrows[0][0].title);
          console.log(formData);
          console.log(editStore.getData().items[selectedrows.length - 1].data.title.film_id);

          if (form.isValid()) {
            form.submit({
              url: "http://localhost:8080/MovieApp_war_exploded/Editdata",
              params: {
                film_id: editStore.getData().items[selectedrows.length - 1].data.title.film_id,
              },
              success: function (form, action) {
                Ext.Msg.alert("Success!", action.response.status);
                store.reload();
                Edi.hide();
                // console.log(action.response.status);
              },
              // params: {form.submit.getParams()},
              failure: function (form, action) {
                Ext.Msg.alert("Failed", "Please try again!");
                Edi.hide();
                // console.log(action.response.status);
              },
            });
          }
        },
      },

      {
        text: "Cancel",
        handler: function () {
          Edi.close();
        },
      },
    ],
    store: Ext.create("Ext.data.ArrayStore", {}),
  },
});
// let toast = Ext.create("Ext.window.Toast",{
//   id: toast,
//     html: 'Data deleted',
//      title: 'My Title',
//      width: 200,
//     align: 't'
//  });
let deleteStore = Ext.create("Ext.data.Store", {
  fields: ["film_id"],
  storeId: "deleteStore",
  data: selectedrows,
});

let Del = Ext.create("Ext.window.Window", {
  title: "Delete Row",
  height: 200,
  width: 300,
  closeAction: "hide",
  target: document.getElementById("deleteButton"),
  layout: "fit",

  html: "<center><h2>Are You sure you want to Delete?<h2></center>",
  //  items:[ {
  //   xtype: toast,
  //   id: toast,
  //   html: 'Data Saved',
  //   title: 'My Title',
  //   width: 200,
  //   align: 't'
  // }],
  buttons: [
    {
      text: "Delete",
      margin: "0 20 0 0",
      handler: function () {
        Ext.Ajax.request({
          url: "http://localhost:8080/MovieApp_war_exploded/Deletedata",
          method: "POST",
          // timeout: 60000,
          jsonData: {
            film_id: deleteData,
          },
          headers: {
            "Content-Type": "application/json",
          },
          success: function (response) {
            Ext.Msg.alert("Deleted");
          },
          failure: function (response) {
            Ext.Msg.alert("Status", "Request Failed.");
          },
        });

        Del.hide();
      },
    },
    {
      text: "cancel",
      margin: "0 20 0 0",
      handler: function () {
        Del.hide();
      },
    },
  ],
  store: Ext.create("Ext.data.ArrayStore", {}),
});

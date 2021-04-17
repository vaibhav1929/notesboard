/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function saveNote(){
    let mode = (document.getElementById("note_save_btn").getAttribute("data"));
    data = {action:"save_note", title:document.getElementById("note_title").value, 
    content:document.getElementById("note_content").value, groupid:document.getElementById("note_groupid").value};
        if(mode == "edit"){
            data.action = "edit_note";
            data.nid = document.getElementById("note_save_btn").getAttribute("editid");
        }
        $.ajax({
        url:"NotesController" ,
        data:data,
        method:"post",
        success:function(res){
            if(res.error){
                swal({text:'Error occured!', icon:'error'});
            }
            else{
                if(mode == "edit") window.location.href = "dashboard.jsp";
                else {
                    document.getElementById("note_title").value = "";
                    document.getElementById("note_content").value = "";
                    res.groupName = $("#note_groupid option:selected").text();
                    addNoteInUI(res);
                }
            }
             
        },
        error:function(){swal({text:'Error occured!', icon:'error'});}
    
    });
}

function deleteNote(nid){
    data = {action:"delete_note", nid:nid};
    console.log(data);
        $.ajax({
        url:"NotesController" ,
        data:data,
        method:"post",
        success:function(res){
            if(res.result == "error"){
                swal({text:'Error occured!', icon:'error'});
            }
            else{
                swal({text:'Note Removed', icon:'success'});
                document.getElementById("note_"+nid).remove();
            } 
        },
        error:function(){swal({text:'Error occured!', icon:'error'});}
    
    });
}

function editNote(nid, title, content, groupid){
    document.getElementById("note_form_title").innerHTML = "Edit "+title+" Note";
    let groups = document.getElementById("note_groupid");
    const options = Array.from(groups.options);
    options.forEach((option, i) => {
        if (option.value == groupid) groups.selectedIndex = i;
    });
    document.getElementById("note_title").value = title;
    document.getElementById("note_content").value = content;
    document.getElementById("NoteFormContainer").scrollIntoView();
    document.getElementById("note_save_btn").setAttribute("data","edit");
    document.getElementById("note_save_btn").setAttribute("editid",nid);
}

function addNoteInUI(note){
    let parent = document.getElementById("group_"+note.groupid);
    if(parent == null){
        parent = document.createElement("div");
        parent.classList.add("row", "mt-4", "mb-4", "p-2", "bg-default", "shadow", "rounded");
        document.getElementById("NoteContainer").appendChild(parent);
        parent.id = "group_"+note.groupid;

        let groupTitle = "<div class='col-12'><h1><span class='badge badge-light'>"+note.groupName+"</span></h1></div>";
        parent.innerHTML = groupTitle;
    }

    let options = "<div class=\"dropdown float-right\">\n" +
        "                            <a class=\"btn btn-icon-only text-primary\" href=\"#\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
        "                              <i class=\"ni ni-bullet-list-67\"></i>\n" +
        "                            </a>\n" +
        "                            <div class=\"dropdown-menu dropdown-menu-right dropdown-menu-arrow\" style=\"\">\n" +
        "                               <button class=\"dropdown-item text-info\" onclick=\"editNote("+note.nid+",'"+note.title+"','"+note.content+"',"+note.groupid+")\">\n" +
    "                                <i class=\"ni ni-scissors\"></i>Edit\n" +
    "                               </button>\n" +
    "                              <button class=\"dropdown-item text-danger\" onclick=\"deleteNote("+note.nid+")\">\n" +
    "                                <i class=\"ni ni-fat-remove \"></i>Remove\n" +
    "                              </button>\n" +
    "                            </div>\n" +
    "                      </div>";

    let content = " <div class='card m-0'><div class='card-header p-2'><h1 style='display: inline-block'><span class='badge badge-info'>"+note.title+"</span></h1>"+options+"</div><div class='card-body'>"+note.content+"</div></div>";
    
    let column = document.createElement("div");
    column.classList.add("col-md-4", "col-sm-4", "pb-2", "pt-2", "pl-2", "pr-2", "m-0");
    column.id = "note_"+note.nid;
    column.innerHTML = content;
    parent.appendChild(column);
}

//---------------------------------GROUP----------------------------------------
function saveGroup(){
    let mode = (document.getElementById("group_save_btn").getAttribute("data"));
    data = {action:"save_group", title:document.getElementById("group_title").value};
        if(mode == "edit"){
            data.action = "edit_group";
            data.groupid = document.getElementById("group_save_btn").getAttribute("editid");
        }
        $.ajax({
        url:"NotesController" ,
        data:data,
        method:"post",
        success:function(res){
            if(res.error){
                swal({text:'Error occured!', icon:'error'});
            }
            else{
                if(mode == "edit") window.location.href = "dashboard.jsp";
                else swal({text:document.getElementById("group_title").value+' Group created', icon:'success'});
            }
             
        },
        error:function(){swal({text:'Error occured!', icon:'error'});}
    
    });
}

function deleteGroup(groupid){
    data = {action:"delete_group", groupid:groupid};
    console.log(data);
        $.ajax({
        url:"NotesController" ,
        data:data,
        method:"post",
        success:function(res){
            if(res.result == "error"){
                swal({text:'Error occured!', icon:'error'});
            }
            else{
                swal({text:'Group Removed', icon:'success'});
                document.getElementById(groupid).remove();
            } 
        },
        error:function(){swal({text:'Error occured!', icon:'error'});}
    
    });
}

function editGroup(groupid, title){
    document.getElementById("group_form_title").innerHTML = "Edit "+title+" Group";
   
    document.getElementById("group_title").value = title;
    document.getElementById("groupFormContainer").scrollIntoView();
    document.getElementById("group_save_btn").setAttribute("data","edit");
    document.getElementById("group_save_btn").setAttribute("editid",groupid);
}

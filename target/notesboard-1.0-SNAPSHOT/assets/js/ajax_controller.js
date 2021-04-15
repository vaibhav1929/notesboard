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
                else addNoteInUI(res);
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
                document.getElementById(nid).remove();
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
    let parent = document.getElementById(note.groupid);
    if(parent == null){
        parent = document.createElement("div");
        parent.classList.add("row", "mt-4", "mb-4", "p-2", "bg-default", "shadow", "rounded");
        document.getElementById("NoteContainer").appendChild(parent);
    }
    let content = " <div class='card m-0'><div class='card-header p-2'><h1> <span class='badge badge-info'>"+note.title+"</span></h1></div><div class='card-body'>"+note.content+"</div></div>";
    let column = document.createElement("div");
    column.classList.add("col-md-4", "col-sm-4", "pb-2", "pt-2", "pl-2", "pr-2", "m-0");
    column.innerHTML = content;
    parent.appendChild(column);
}
package com.example.aistudybuddy.screens

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aistudybuddy.components.BottomNavigationBar



// ======================================================
// DATA
// ======================================================

data class StudyFolder(
    val name: String
)

data class StudyNote(
    val id: Int,
    val subject: String,
    val title: String,
    val content: String,
    val pdfUri: String? = null
)


// ======================================================
// STUDY NOTES SCREEN
// ======================================================

@Composable
fun StudyNotesScreen(
    onBackClick: () -> Unit = {},
    onHomeClick: () -> Unit = {},
    onAssignmentsClick: () -> Unit = {},
    onPlannerClick: () -> Unit = {},
    onProgressClick: () -> Unit = {},
    onProfileClick: () -> Unit = {}
) {

    val context = LocalContext.current

    // ==================================================
    // FOLDERS
    // ==================================================

    val folders = remember {
        mutableStateListOf(
            StudyFolder("Mathematics"),
            StudyFolder("Physics"),
            StudyFolder("Biology")
        )
    }


    // ==================================================
    // NOTES
    // ==================================================

    val notes = remember {
        mutableStateListOf(

            StudyNote(
                id = 1,
                subject = "Mathematics",
                title = "Algebra",
                content = "Important algebra formulas and examples."
            ),

            StudyNote(
                id = 2,
                subject = "Mathematics",
                title = "Calculus",
                content = "Differentiation and integration notes."
            ),

            StudyNote(
                id = 3,
                subject = "Physics",
                title = "Motion",
                content = "Notes about velocity, acceleration and motion."
            ),

            StudyNote(
                id = 4,
                subject = "Biology",
                title = "Cell Structure",
                content = "Important parts and functions of a cell."
            )
        )
    }


    // ==================================================
    // SEARCH
    // ==================================================

    var searchText by remember {
        mutableStateOf("")
    }


    // ==================================================
    // SELECTED SUBJECT
    // ==================================================

    var selectedSubject by remember {
        mutableStateOf<String?>(null)
    }


    // ==================================================
    // ADD FOLDER
    // ==================================================

    var showAddFolderDialog by remember {
        mutableStateOf(false)
    }


    // ==================================================
    // EDIT FOLDER
    // ==================================================

    var folderBeingEdited by remember {
        mutableStateOf<StudyFolder?>(null)
    }


    // ==================================================
    // DELETE FOLDER
    // ==================================================

    var folderBeingDeleted by remember {
        mutableStateOf<StudyFolder?>(null)
    }


    // ==================================================
    // PDF
    // ==================================================

    var selectedPdfUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var showPdfNameDialog by remember {
        mutableStateOf(false)
    }


    // ==================================================
    // EDIT NOTE
    // ==================================================

    var editingNote by remember {
        mutableStateOf<StudyNote?>(null)
    }


    // ==================================================
    // PDF PICKER
    // ==================================================

    val pdfPickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->

            if (uri != null) {

                selectedPdfUri = uri

                showPdfNameDialog = true
            }
        }

    // ==================================================
    // MAIN SCREEN
    // ==================================================

    Scaffold(

        bottomBar = {

            BottomNavigationBar(
                selectedItem = "Notes",

                onHomeClick = onHomeClick,

                onAssignmentsClick = onAssignmentsClick,

                onPlannerClick = onPlannerClick,

                onProgressClick = onProgressClick,

                onProfileClick = onProfileClick,
            )
        }

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {




            // ==================================================
            // MAIN FOLDER PAGE
            // ==================================================

            if (selectedSubject == null) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {

                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    Text(
                        text = "Study Notes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF252838)
                    )


                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )


                    // ------------------------------------------
                    // SEARCH
                    // ------------------------------------------

                    OutlinedTextField(
                        value = searchText,

                        onValueChange = {
                            searchText = it
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),

                        placeholder = {
                            Text(
                                text = "Search notes",
                                fontSize = 12.sp,
                                color = Color(0xFF8A8C96)
                            )
                        },

                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search notes",
                                tint = Color(0xFF8A8C96)
                            )
                        },

                        singleLine = true,

                        shape = RoundedCornerShape(16.dp)
                    )


                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )


                    // ------------------------------------------
                    // FOLDER LIST
                    // ------------------------------------------

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp),

                        contentPadding =
                            PaddingValues(bottom = 20.dp)
                    ) {

                        items(
                            items = folders,

                            key = {
                                it.name
                            }
                        ) { folder ->

                            val folderNotes =
                                notes.filter {
                                    it.subject == folder.name
                                }


                            val showFolder =

                                searchText.isBlank() ||

                                        folder.name.contains(
                                            searchText,
                                            ignoreCase = true
                                        ) ||

                                        folderNotes.any {

                                            it.title.contains(
                                                searchText,
                                                ignoreCase = true
                                            )
                                        }


                            if (showFolder) {

                                NoteFolderCard(

                                    title =
                                        folder.name,

                                    notes =
                                        "${folderNotes.size} Notes",

                                    folderColor =
                                        getFolderColor(
                                            folder.name
                                        ),

                                    iconColor =
                                        getFolderIconColor(
                                            folder.name
                                        ),

                                    onClick = {

                                        selectedSubject =
                                            folder.name
                                    },

                                    onEdit = {

                                        folderBeingEdited =
                                            folder
                                    },

                                    onDelete = {

                                        folderBeingDeleted =
                                            folder
                                    }
                                )
                            }
                        }


                        // ------------------------------------------
                        // ADD SUBJECT BUTTON
                        // ------------------------------------------

                        item {

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )


                            Button(

                                onClick = {

                                    showAddFolderDialog =
                                        true
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp),

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            Color(0xFF4169E1),

                                        contentColor =
                                            Color.White
                                    ),

                                shape =
                                    RoundedCornerShape(9.dp)
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Add,

                                    contentDescription =
                                        "Add subject",

                                    modifier =
                                        Modifier.size(18.dp)
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(4.dp)
                                )


                                Text(

                                    text =
                                        "Add Notes",

                                    fontSize = 12.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }


                        // ------------------------------------------
                        // AI STUDY TIP
                        // ------------------------------------------

                        item {

                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )


                            Box(

                                modifier = Modifier

                                    .fillMaxWidth()

                                    .background(
                                        color =
                                            Color(0xFFF5F0FF),

                                        shape =
                                            RoundedCornerShape(10.dp)
                                    )

                                    .border(
                                        width = 0.5.dp,

                                        color =
                                            Color(0xFFD9CCFF),

                                        shape =
                                            RoundedCornerShape(10.dp)
                                    )

                                    .padding(12.dp)
                            ) {

                                Row(

                                    modifier =
                                        Modifier.fillMaxWidth(),

                                    verticalAlignment =
                                        Alignment.Top
                                ) {

                                    Text(

                                        text = "✦",

                                        fontSize = 16.sp,

                                        color =
                                            Color(0xFF7657D9)
                                    )


                                    Spacer(
                                        modifier =
                                            Modifier.width(8.dp)
                                    )


                                    Column(

                                        modifier =
                                            Modifier.weight(1f)
                                    ) {

                                        Text(

                                            text =
                                                "AI Study Tip",

                                            fontSize = 12.sp,

                                            fontWeight =
                                                FontWeight.Bold,

                                            color =
                                                Color(0xFF7657D9)
                                        )


                                        Spacer(
                                            modifier =
                                                Modifier.height(4.dp)
                                        )


                                        Text(

                                            text =
                                                "Review your notes regularly to improve retention. Great job staying consistent!",

                                            fontSize = 10.sp,

                                            color =
                                                Color(0xFF555863),

                                            lineHeight =
                                                14.sp
                                        )
                                    }


                                    Text(

                                        text = "›",

                                        fontSize = 20.sp,

                                        color =
                                            Color(0xFF7657D9)
                                    )
                                }
                            }
                        }
                    }
                }


            } else {

                // ==================================================
                // INSIDE SUBJECT
                // ==================================================

                Column(
                    modifier =
                        Modifier.fillMaxSize()
                ) {

                    // ------------------------------------------
                    // BACK BUTTON
                    // ------------------------------------------

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        IconButton(

                            onClick = {

                                selectedSubject =
                                    null
                            }
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.ArrowBack,

                                contentDescription =
                                    "Back"
                            )
                        }


                        Text(

                            text =
                                selectedSubject!!,

                            fontSize = 17.sp,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                Color(0xFF252838)
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    // ------------------------------------------
                    // NOTES LIST
                    // ------------------------------------------

                    LazyColumn(

                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),

                        contentPadding =
                            PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),

                        verticalArrangement =
                            Arrangement.spacedBy(8.dp)
                    ) {

                        items(

                            items =
                                notes.filter {

                                    it.subject ==
                                            selectedSubject
                                },

                            key = {

                                it.id
                            }

                        ) { note ->

                            NoteItemCard(

                                note = note,

                                onEdit = {

                                    editingNote =
                                        note
                                },

                                onDelete = {

                                    notes.remove(
                                        note
                                    )
                                }
                            )
                        }


                        // ------------------------------------------
                        // UPLOAD PDF
                        // ------------------------------------------

                        item {

                            Spacer(
                                modifier =
                                    Modifier.height(8.dp)
                            )


                            Button(

                                onClick = {

                                    pdfPickerLauncher.launch(

                                        arrayOf(
                                            "application/pdf"
                                        )
                                    )
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(42.dp),

                                colors =
                                    ButtonDefaults.buttonColors(
                                        containerColor =
                                            Color(0xFF4169E1)
                                    ),

                                shape =
                                    RoundedCornerShape(9.dp)
                            ) {

                                Icon(

                                    imageVector =
                                        Icons.Default.Add,

                                    contentDescription =
                                        "Upload PDF"
                                )


                                Spacer(
                                    modifier =
                                        Modifier.width(4.dp)
                                )


                                Text(

                                    text =
                                        "Add Notes",

                                    fontSize = 12.sp,

                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }
                        }


                        item {

                            Spacer(
                                modifier =
                                    Modifier.height(80.dp)
                            )
                        }
                    }
                }
            }
        }
    }


    // ==================================================
    // ADD SUBJECT DIALOG
    // ==================================================

    if (showAddFolderDialog) {

        AddFolderDialog(

            onDismiss = {

                showAddFolderDialog =
                    false
            },

            onSave = { subject ->

                val exists =
                    folders.any {

                        it.name.equals(
                            subject,
                            ignoreCase = true
                        )
                    }


                if (!exists) {

                    folders.add(
                        StudyFolder(
                            subject
                        )
                    )
                }


                showAddFolderDialog =
                    false
            }
        )
    }


    // ==================================================
    // EDIT SUBJECT DIALOG
    // ==================================================

    folderBeingEdited?.let { folder ->

        EditFolderDialog(

            folder = folder,

            onDismiss = {

                folderBeingEdited =
                    null
            },

            onSave = { newName ->

                val exists =
                    folders.any {

                        it.name.equals(
                            newName,
                            ignoreCase = true
                        ) &&
                                it.name != folder.name
                    }


                if (!exists) {

                    val index =
                        folders.indexOfFirst {

                            it.name ==
                                    folder.name
                        }


                    if (index != -1) {

                        folders[index] =
                            StudyFolder(
                                newName
                            )
                    }


                    // Update notes belonging
                    // to the renamed folder

                    notes.replaceAll { note ->

                        if (
                            note.subject ==
                            folder.name
                        ) {

                            note.copy(
                                subject =
                                    newName
                            )

                        } else {

                            note
                        }
                    }


                    // If currently inside
                    // this folder, update
                    // the selected subject

                    if (
                        selectedSubject ==
                        folder.name
                    ) {

                        selectedSubject =
                            newName
                    }
                }


                folderBeingEdited =
                    null
            }
        )
    }


    // ==================================================
    // DELETE SUBJECT CONFIRMATION
    // ==================================================

    folderBeingDeleted?.let { folder ->

        AlertDialog(

            onDismissRequest = {

                folderBeingDeleted =
                    null
            },

            title = {

                Text(
                    text =
                        "Delete Subject?"
                )
            },

            text = {

                Text(
                    text =
                        "Are you sure you want to delete \"${folder.name}\" and all its notes?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        folders.remove(
                            folder
                        )

                        notes.removeAll {

                            it.subject ==
                                    folder.name
                        }

                        folderBeingDeleted =
                            null
                    }
                ) {

                    Text(
                        text =
                            "Delete"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        folderBeingDeleted =
                            null
                    }
                ) {

                    Text(
                        text =
                            "Cancel"
                    )
                }
            }
        )
    }


    // ==================================================
    // NAME PDF DIALOG
    // ==================================================

    if (showPdfNameDialog) {

        NamePdfDialog(

            onDismiss = {

                showPdfNameDialog =
                    false

                selectedPdfUri =
                    null
            },

            onSave = { pdfName ->

                val uri =
                    selectedPdfUri

                val subject =
                    selectedSubject


                if (
                    uri != null &&
                    subject != null
                ) {

                    val newId =
                        (notes.maxOfOrNull {

                            it.id

                        } ?: 0) + 1


                    val finalName =

                        if (
                            pdfName
                                .lowercase()
                                .endsWith(".pdf")
                        ) {

                            pdfName

                        } else {

                            "$pdfName.pdf"
                        }


                    notes.add(

                        StudyNote(

                            id =
                                newId,

                            subject =
                                subject,

                            title =
                                finalName,

                            content =
                                "PDF Document",

                            pdfUri =
                                uri.toString()
                        )
                    )
                }


                showPdfNameDialog =
                    false

                selectedPdfUri =
                    null
            }
        )
    }


    // ==================================================
    // EDIT NOTE DIALOG
    // ==================================================

    editingNote?.let { note ->

        EditNoteDialog(

            note = note,

            onDismiss = {

                editingNote =
                    null
            },

            onSave = { title, content ->

                val index =
                    notes.indexOfFirst {

                        it.id ==
                                note.id
                    }


                if (index != -1) {

                    notes[index] =
                        note.copy(

                            title =
                                title,

                            content =
                                content
                        )
                }


                editingNote =
                    null
            }
        )
    }
}


// ======================================================
// FOLDER CARD
// ======================================================

@Composable
fun NoteFolderCard(

    title: String,

    notes: String,

    folderColor: Color,

    iconColor: Color,

    onClick: () -> Unit,

    onEdit: () -> Unit,

    onDelete: () -> Unit
) {

    var showMenu by remember {

        mutableStateOf(false)
    }


    Box(

        modifier = Modifier

            .fillMaxWidth()

            .height(62.dp)

            .background(

                color =
                    Color.White,

                shape =
                    RoundedCornerShape(9.dp)
            )

            .border(

                width = 0.5.dp,

                color =
                    Color(0xFFE0E0E5),

                shape =
                    RoundedCornerShape(9.dp)
            )

            .clickable {

                onClick()
            }

            .padding(horizontal = 10.dp),

        contentAlignment =
            Alignment.Center
    ) {

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(

                modifier = Modifier

                    .size(38.dp)

                    .background(

                        color =
                            folderColor,

                        shape =
                            RoundedCornerShape(7.dp)
                    ),

                contentAlignment =
                    Alignment.Center
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Folder,

                    contentDescription =
                        title,

                    tint =
                        iconColor,

                    modifier =
                        Modifier.size(24.dp)
                )
            }


            Spacer(
                modifier =
                    Modifier.width(12.dp)
            )


            Column(

                modifier =
                    Modifier.weight(1f)
            ) {

                Text(

                    text =
                        title,

                    fontSize =
                        13.sp,

                    fontWeight =
                        FontWeight.SemiBold,

                    color =
                        Color(0xFF252838)
                )


                Spacer(
                    modifier =
                        Modifier.height(2.dp)
                )


                Text(

                    text =
                        notes,

                    fontSize =
                        10.sp,

                    color =
                        Color(0xFF70727D)
                )
            }


            // ==================================================
            // THREE DOT MENU
            // ==================================================

            Box {

                IconButton(

                    onClick = {

                        showMenu =
                            true
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.MoreVert,

                        contentDescription =
                            "More options",

                        tint =
                            Color(0xFF70727D),

                        modifier =
                            Modifier.size(20.dp)
                    )
                }


                DropdownMenu(

                    expanded =
                        showMenu,

                    onDismissRequest = {

                        showMenu =
                            false
                    }

                ) {

                    // ------------------------------------------
                    // EDIT
                    // ------------------------------------------

                    DropdownMenuItem(

                        text = {

                            Text(
                                text =
                                    "Edit"
                            )
                        },

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription =
                                    null
                            )
                        },

                        onClick = {

                            showMenu =
                                false

                            onEdit()
                        }
                    )


                    // ------------------------------------------
                    // DELETE
                    // ------------------------------------------

                    DropdownMenuItem(

                        text = {

                            Text(
                                text =
                                    "Delete"
                            )
                        },

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Default.Delete,

                                contentDescription =
                                    null
                            )
                        },

                        onClick = {

                            showMenu =
                                false

                            onDelete()
                        }
                    )
                }
            }
        }
    }
}


// ======================================================
// NOTE CARD
// ======================================================

@Composable
fun NoteItemCard(

    note: StudyNote,

    onEdit: () -> Unit,

    onDelete: () -> Unit

) {

    val context = LocalContext.current

    var showMenu by remember {
        mutableStateOf(false)
    }


    Box(

        modifier = Modifier

            .fillMaxWidth()

            .background(
                Color.White,
                RoundedCornerShape(9.dp)
            )

            .border(
                0.5.dp,
                Color(0xFFE0E0E5),
                RoundedCornerShape(9.dp)
            )

            .clickable {

                if (note.pdfUri != null) {

                    val intent = Intent(
                        Intent.ACTION_VIEW
                    ).apply {

                        setDataAndType(
                            Uri.parse(note.pdfUri),
                            "application/pdf"
                        )

                        addFlags(
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )
                    }

                    try {

                        context.startActivity(intent)

                    } catch (e: Exception) {

                        // No PDF application available
                    }
                }
            }

            .padding(12.dp)
    ) {

        Row(

            modifier =
                Modifier.fillMaxWidth(),

            verticalAlignment =
                Alignment.Top
        ) {

            if (note.pdfUri != null) {

                Icon(

                    imageVector =
                        Icons.Default.PictureAsPdf,

                    contentDescription =
                        "PDF",

                    tint =
                        Color(0xFFD32F2F),

                    modifier =
                        Modifier.size(30.dp)
                )


                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )
            }


            Column(

                modifier =
                    Modifier.weight(1f)
            ) {

                Text(

                    text =
                        note.title,

                    fontSize =
                        13.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        Color(0xFF252838)
                )


                Spacer(
                    modifier =
                        Modifier.height(5.dp)
                )


                Text(

                    text =
                        note.content,

                    fontSize =
                        11.sp,

                    color =
                        Color(0xFF70727D),

                    lineHeight =
                        16.sp
                )
            }


            Box {

                IconButton(

                    onClick = {

                        showMenu =
                            true
                    }
                ) {

                    Icon(

                        imageVector =
                            Icons.Default.MoreVert,

                        contentDescription =
                            "More options"
                    )
                }


                DropdownMenu(

                    expanded =
                        showMenu,

                    onDismissRequest = {

                        showMenu =
                            false
                    }

                ) {

                    DropdownMenuItem(

                        text = {

                            Text(
                                text =
                                    "Edit"
                            )
                        },

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription =
                                    null
                            )
                        },

                        onClick = {

                            showMenu =
                                false

                            onEdit()
                        }
                    )


                    DropdownMenuItem(

                        text = {

                            Text(
                                text =
                                    "Delete"
                            )
                        },

                        leadingIcon = {

                            Icon(

                                imageVector =
                                    Icons.Default.Delete,

                                contentDescription =
                                    null
                            )
                        },

                        onClick = {

                            showMenu =
                                false

                            onDelete()
                        }
                    )
                }
            }
        }
    }
}


// ======================================================
// ADD FOLDER DIALOG
// ======================================================

@Composable
fun AddFolderDialog(

    onDismiss: () -> Unit,

    onSave: (String) -> Unit
) {

    var subject by remember {

        mutableStateOf("")
    }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(
                text =
                    "Add Subject"
            )
        },

        text = {

            OutlinedTextField(

                value =
                    subject,

                onValueChange = {

                    subject =
                        it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {

                    Text(
                        text =
                            "Subject"
                    )
                },

                singleLine = true
            )
        },

        confirmButton = {

            TextButton(

                onClick = {

                    if (
                        subject.isNotBlank()
                    ) {

                        onSave(
                            subject.trim()
                        )
                    }
                }
            ) {

                Text(
                    text =
                        "Save"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick =
                    onDismiss
            ) {

                Text(
                    text =
                        "Cancel"
                )
            }
        }
    )
}


// ======================================================
// EDIT FOLDER DIALOG
// ======================================================

@Composable
fun EditFolderDialog(

    folder: StudyFolder,

    onDismiss: () -> Unit,

    onSave: (String) -> Unit
) {

    var subject by remember {

        mutableStateOf(
            folder.name
        )
    }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(
                text =
                    "Edit Subject"
            )
        },

        text = {

            OutlinedTextField(

                value =
                    subject,

                onValueChange = {

                    subject =
                        it
                },

                modifier =
                    Modifier.fillMaxWidth(),

                label = {

                    Text(
                        text =
                            "Subject"
                    )
                },

                singleLine = true
            )
        },

        confirmButton = {

            TextButton(

                onClick = {

                    if (
                        subject.isNotBlank()
                    ) {

                        onSave(
                            subject.trim()
                        )
                    }
                }
            ) {

                Text(
                    text =
                        "Save"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick =
                    onDismiss
            ) {

                Text(
                    text =
                        "Cancel"
                )
            }
        }
    )
}


// ======================================================
// NAME PDF DIALOG
// ======================================================

@Composable
fun NamePdfDialog(

    onDismiss: () -> Unit,

    onSave: (String) -> Unit
) {

    var pdfName by remember {

        mutableStateOf("")
    }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(
                text =
                    "Name PDF"
            )
        },

        text = {

            Column {

                Text(

                    text =
                        "Enter a name for your PDF:",

                    fontSize =
                        12.sp,

                    color =
                        Color(0xFF70727D)
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                OutlinedTextField(

                    value =
                        pdfName,

                    onValueChange = {

                        pdfName =
                            it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    label = {

                        Text(
                            text =
                                "PDF Name"
                        )
                    },

                    singleLine = true
                )
            }
        },

        confirmButton = {

            TextButton(

                onClick = {

                    if (
                        pdfName.isNotBlank()
                    ) {

                        onSave(
                            pdfName.trim()
                        )
                    }
                }
            ) {

                Text(
                    text =
                        "Save"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick =
                    onDismiss
            ) {

                Text(
                    text =
                        "Cancel"
                )
            }
        }
    )
}


// ======================================================
// EDIT NOTE DIALOG
// ======================================================

@Composable
fun EditNoteDialog(

    note: StudyNote,

    onDismiss: () -> Unit,

    onSave:
        (String, String) -> Unit
) {

    var title by remember {

        mutableStateOf(
            note.title
        )
    }


    var content by remember {

        mutableStateOf(
            note.content
        )
    }


    AlertDialog(

        onDismissRequest =
            onDismiss,

        title = {

            Text(
                text =
                    "Edit Note"
            )
        },

        text = {

            Column {

                OutlinedTextField(

                    value =
                        title,

                    onValueChange = {

                        title =
                            it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    label = {

                        Text(
                            text =
                                "Note Title"
                        )
                    },

                    singleLine = true
                )


                Spacer(
                    modifier =
                        Modifier.height(8.dp)
                )


                OutlinedTextField(

                    value =
                        content,

                    onValueChange = {

                        content =
                            it
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    label = {

                        Text(
                            text =
                                "Note Content"
                        )
                    },

                    minLines = 4
                )
            }
        },

        confirmButton = {

            TextButton(

                onClick = {

                    if (
                        title.isNotBlank() &&
                        content.isNotBlank()
                    ) {

                        onSave(

                            title.trim(),

                            content.trim()
                        )
                    }
                }
            ) {

                Text(
                    text =
                        "Save"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick =
                    onDismiss
            ) {

                Text(
                    text =
                        "Cancel"
                )
            }
        }
    )
}


// ======================================================
// FOLDER COLORS
// ======================================================

private fun getFolderColor(
    subject: String
): Color {

    return when {

        subject.equals(
            "Mathematics",
            ignoreCase = true
        ) ->
            Color(0xFFE7D5FF)


        subject.equals(
            "Physics",
            ignoreCase = true
        ) ->
            Color(0xFFD7E8FF)


        subject.equals(
            "Biology",
            ignoreCase = true
        ) ->
            Color(0xFFD9F1D2)


        else ->
            Color(0xFFFFE8D6)
    }
}


private fun getFolderIconColor(
    subject: String
): Color {

    return when {

        subject.equals(
            "Mathematics",
            ignoreCase = true
        ) ->
            Color(0xFF8D5DEB)


        subject.equals(
            "Physics",
            ignoreCase = true
        ) ->
            Color(0xFF4D8DE8)


        subject.equals(
            "Biology",
            ignoreCase = true
        ) ->
            Color(0xFF69B65D)


        else ->
            Color(0xFFE58A3D)
    }
}
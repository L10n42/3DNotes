package com.kappdev.notes.feature_notes.domain.use_cases

data class NotesUseCases(
    val moveNotesTo: MoveNotesTo,
    val moveTodoListsTo: MoveTodoListsTo,
    val getAllData: GetAllData,
    val getAllDataByFolder: GetAllDataByFolder,
    val insertNote: InsertNote,
    val insertFolder: InsertFolder,
    val getNotes: GetNotes,
    val getTodoLists: GetTodoLists,
    val getNoteById: GetNoteById,
    val getNotesByFolderId: GetNotesByFolderId,
    val getFolderById: GetFolderById,
    val getFolders: GetFolders,
    val removeFolder: RemoveFolder,
    val removeNote: RemoveNote,
    val removeNoteById: RemoveNoteById,
    val multipleRemove: MultipleRemove,
    val insertTodoList: InsertTodoList,
    val getTodoListById: GetTodoListById,
    val removeTodoListById: RemoveTodoListById,
)

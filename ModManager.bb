Type Mods
    Field Name$
    Field Description$
    Field Path$
End Type

Function ReloadMods()
    Delete Each Mods
    d% = ReadDir("Mods")
    Repeat
        f$=NextFile(d)
        If f$="" Then Exit
        If f$<>"." And f$<>".." And FileType("Mods\"+f) = 2 Then
            m.Mods = new Mods
            m\Name = file
            m\Path = "Mods\"+f+"\"
        EndIf
    Forever
    CloseDir(d)
End Function

Function LoadModdedTextureNonStrict%(file$, flags%)
    Local ext$ = File_GetExtension(File)
	Local fileNoExt$ = Left(File, Len(File) - Len(ext))
	Local tmp%

	For m.Mods = Each Mods
		For i = 0 To ImageExtensionCount
			Local usedExtension$
			If i = ImageExtensionCount Then
				usedExtension = ext
			Else
				usedExtension = ImageExtensions[i]
			EndIf
			Local modPath$ = m\Path + fileNoExt + usedExtension
			If FileType(modPath) = 1 Then
				tmp = LoadTexture(modPath, flags)
				If tmp <> 0 Then
					Return tmp
				Else If DebugResourcePacks Then
					RuntimeError("Failed to load texture " + Chr(34) + modPath + Chr(34) + ".")
				EndIf
			EndIf
		Next
	Next

    Return LoadTexture(file, flags)
End Function

Function DetermineModdedPath$(f$)
    For m.Mods = Each Mods
        modPath$ = m\Path + f
        If FileType(modPath) = 1 Then Return modPath
    Next
    Return f
End Function

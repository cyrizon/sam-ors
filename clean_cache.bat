@echo off
REM Vide le dossier elevation_cache
IF EXIST elevation_cache (
    del /q /f elevation_cache\*
    for /d %%i in (elevation_cache\*) do rmdir /s /q "%%i"
    echo Contenu de elevation_cache supprimé.
) ELSE (
    echo Dossier elevation_cache non trouvé.
)

REM Vide le dossier graphs
IF EXIST graphs (
    del /q /f graphs\*
    for /d %%i in (graphs\*) do rmdir /s /q "%%i"
    echo Contenu de graphs supprimé.
) ELSE (
    echo Dossier graphs non trouvé.
)
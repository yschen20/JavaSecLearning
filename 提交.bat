@echo off
chcp 65001 >nul

:: 获取当前时间
for /f "tokens=1-2" %%a in ('powershell -Command "Get-Date -Format 'yyyy-MM-dd HH:mm:ss'"') do (
    set commit_message=Updated: %%a %%b
)

:: 执行 Git 命令
git add .
git commit -m "%commit_message%"
git push --force origin main
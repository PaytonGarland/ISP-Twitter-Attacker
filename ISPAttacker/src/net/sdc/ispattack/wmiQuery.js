var arrComputers = new Array("\localhost");

var objWMIService = GetObject("winmgmts:\\\\" + arrComputers[i] + "\\root\\CIMV2");
   var colItems = objWMIService.ExecQuery("SELECT * FROM Win32_PerfFormattedData_Tcpip_NetworkInterface");
while (!items.atEnd())
{
    WScript.Echo(items.item().Name);
    items.moveNext();
}
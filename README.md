# Windows Admin Util

Library for running Java applications as Admin in Windows.\
Only works with the compiled .jar-file, not when executed in an IDE.

## Usage

```java
package main;

import de.nkilders.win.adminutil.AdminUtil;

public class MyClass {
    public static void main(String[] args) {
        if (!AdminUtil.isAdmin()) {
            AdminUtil.runAsAdmin();
            System.exit(0);
        }

        // your code...
    }
}
```
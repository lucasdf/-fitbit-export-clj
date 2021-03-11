# fitbit-backup-clj

CLI application to export data from [Fitbit](https://dev.fitbit.com/build/reference/web-api/)

## Usage
1. Create a new application https://dev.fitbit.com/apps/new
- set **OAuth 2.0 Application Type** to **Personal**
- set **Callback URL** to **http://localhost:8080/auth/callback**
2. Add client-id and client-secret to [config file](config/config.edn). These are presented after the application registration.
```
{:client-id "XXXXX"
 :client-secret "super_secret"}
```
3. Run `lein run 2021-03-01` to retrieve data. The first time you run this a page will open in your browser to authorize the created application.

## TODO
- [] Sleep data
- [] Activity data
- [] Heart Rate data
- [] Backup script to retrieve data and upload to S3 and/or Dropbox as csv

FIXME

## License

Copyright © 2021 FIXME

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.

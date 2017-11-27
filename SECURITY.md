# Cyber Security considerations

## File Transfer

| Protocol | Port | Speed   | Credentials  | Content   |  Tool       |
| -------- | ---- | ------- | ------------ | --------- | ----------- |
| FTP      | 21   | high    | plain        | plain     | FileZilla   |
| FTPS     |      | high    |              |           |             |
| SFTP     | 22   | medium  | encrypted    | encrypted | GoodSync    |
| WebDAV   | 80   | high    |              |           |             |
| -------- | ---- | ------- | ------------ | --------- | ----------- |

### Recommended: SFTP = FTP over SSH
Enable SSH with a ssh-key encryption:
```bash
$ ssh-keygen -t rsa
$ ssh-copy-id root@hutterer-tik
```
On the NAS:
```bash
$ nano /etc/ssh/sshd_config
service ssh restart
```
enter: PasswordAuthentication no
```bash
$ service ssh restart
```
Forward Port 22 on the router

Use SFTP in GoodSync  

## Linux Security 

### Login - access

Password security:
* Change all default password
* Use random alpha numeric passwords

Patching:
* Set router and NAS to auto update firmware

Enable SSL
Disable all unused protocols

## User security

Where to keep Passwords:
* how to create a strong password: https://lifehacker.com/four-methods-to-create-a-secure-password-youll-actually-1601854240
* KeePassX: open source pwd keeper with strong encryption: https://www.keepassx.org/
* Mac OSX: Keychain free OSS, secured/unlocked with login PWD, encryption with 3DES
	* stored in ~/Library/Keychains/
	* used also be Safari to store credentials
* Chrome uses a SQLite database at:
	* ~/Library/Application Support/Google/Chrome/Default/Login Data

We will full fill the following requirements:
* Users’ passwords should not be recoverable from the database.
* Identical, or even similar, passwords should have different hashes.
* The database should give no hints as to password lengths.

Implementation:
* Store password encrypted in the DB
* Encrypt with HMAC-SHA-256
	* Use a 16 byte salt also known as a nonce, which is short for “number used once.”
	* create it with /dev/uranum for good random numbers
	* Use the salt for the encryption and store it with the user-DB
	* hash = encrypt_hmac_sha_256(salt + pwd), store 32 bytes of the hash
	* Use hash stretching PBKDF2 with e.g. 40.000 iterations (Aug 2017) 
		* to increase the compute time needed for cracking 
		* store the number of iterations with the users id
		* increase the iterations every year to keep up with increasing compute power of the hackers

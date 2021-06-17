#### convert byte[] to string
```java
byte[] a = new byte[10];
a.toString();  // wrong way, this will turn the arr to string ,return memory address
//correct
new String(a,0,a.length);
```
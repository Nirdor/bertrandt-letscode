module letscode {
  requires java.sql;
  requires gson;
  
  opens letscode to gson;
}
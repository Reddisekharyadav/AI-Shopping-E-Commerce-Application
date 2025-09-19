<!-- process.php -->
<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "ai shopping";

$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("Connection failed: ");
}
    echo "Connected successfully";
    $name = $_POST['name'];
    $email = $_POST['email'];
$conn->close();
?>
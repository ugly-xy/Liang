<?php

include $_SERVER['DOCUMENT_ROOT'].'/lib/Encrypt.php';

$str = '333';

$text = encrypt_decrypt('encrypt',$str);

echo $text;

echo PHP_EOL;

$text = encrypt_decrypt('decrypt',$text);

echo $text;

<?php

require('vendor/autoload.php');

use Murich\PhpCryptocurrencyAddressValidation\Validation\ETH;


$validator = new ETH('0x7b9c22805f713c6ac581fd7536e47cd8e8074666');
 
echo $validator->validate()
 

?>
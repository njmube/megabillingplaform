BigLargeNumberOperations = function (){
			
	var self = this;
	
	var decA;
	
	var decB;
	
	var intA;
		
	var intB;
	
	function getDecPlace(x) {
		var x = x;
		x = x + '';
		
		//position of first decimal digit
		var pos = x.indexOf('.') + 1;
		
		//if 0, then no decimals (-1+1)
		if (pos > 0){
			return x.length - pos;
		}
		else
			return 0;
	}
	
	function convertToInteger(x){
		var x = x;
		x = x + '';
		
		x = x.replace('.','');
		
		return x;
	}
	
	function purifyNumbers(a, b){
	
		decA = getDecPlace(a);
		
		decB = getDecPlace(b);				
		
		intA = convertToInteger(a);
		
		intB = convertToInteger(b);				
	}
	
	function checkDecPlaces(){
		if(decA > decB || decB > decA){
			var diff = 0;
			
			if(decA > decB)
				diff = decA - decB;
			else if(decB > decA)
				diff = decB - decA;
			
			var i;
			for(i = 0; i < diff; i++){
				if(decA > decB)
					intB = intB + '0';
				else if(decB > decA)
					intA = intA + '0';
			}
		}
	}
	
	function removeLeadinCeros(x){
		var x = x;
		x = x + '';
		
		while (x.charAt(0) == '0')
		{
			x = x.substring(1, x.length);
		}

		if (x.charAt(0)=='.')

		x = '0' + x;
		
		return x;
	}
	
	function insertDecPlace(num, decPlace, toFixPlace) {
		var num = num;
		var decPlace = decPlace;
		var ans = '';
		var dec;
		if (decPlace > 0){
			ans = num.substring(0,num.length-decPlace) + '.' + num.substring(num.length-decPlace, num.length);
			dec = getDecPlace(ans);
			if(dec > toFixPlace){					
				ans = ans.substring(0, ans.length - (dec - toFixPlace));
				return removeLeadinCeros(ans);
			}
			else{
				var i;
				for(i = 0; i < (toFixPlace - dec); i++){
					ans = ans + '0';
				}
				return removeLeadinCeros(ans);
			}
				
		}
		else
			return removeLeadinCeros(num);
	}		
	
	self.add = function(a, b, p) {
		
		purifyNumbers(a, b);
		
		checkDecPlaces();
		
		if (parseInt(intA) == 0 && parseInt(intB) == 0) {
			var res = '0.';
			var k;
			for(k = 0; k < parseInt(p); k++){
				res = res + '0';
			}
			
			return res;
		}
		
		intA = intA.split('').reverse();
		intB = intB.split('').reverse();
		var result = [];

		for (var i = 0; (intA[i] >= 0) || (intB[i] >= 0); i++) {
			var sum = (parseInt(intA[i]) || 0) + (parseInt(intB[i]) || 0);

			if (!result[i]) {
				result[i] = 0;
			}

			var next = ((result[i] + sum) / 10) | 0;
			result[i] = (result[i] + sum) % 10;

			if (next) {
				result[i + 1] = next;
			}
		}
		
		var finalResult = result.reverse().join('');				
		
		var totalDecPlace = decA;
		
		if(decB > decA)
			totalDecPlace = decB;
		
		var toFixPlace = p;

		return insertDecPlace(finalResult, totalDecPlace, toFixPlace);
	};
	
	self.minus = function(a, b, p) {
		
		purifyNumbers(a, b);
		
		checkDecPlaces();
		
		if (parseInt(intA) == 0 && parseInt(intB) == 0) {
			var res = '0.';
			var k;
			for(k = 0; k < parseInt(p); k++){
				res = res + '0';
			}
			
			return res;
		}				
		
		intA = intA.split('').reverse();
		intB = intB.split('').reverse();
		var result = [];

		for (var i = 0; (intA[i] >= 0) || (intB[i] >= 0); i++) {
			var valA = (parseInt(intA[i]) || 0);
			var valB = (parseInt(intB[i]) || 0);					
			
			if(valA < valB){
				valA = valA + 10;						
			}
			
			if (!result[i]) {
				result[i] = 0;
			}

			var next = (valA / 10) | 0;
			result[i] = valA - valB - result[i];

			if (next) {
				result[i + 1] = next;
			}
		}
		
		var finalResult = result.reverse().join('');				
		
		var totalDecPlace = decA;
		
		if(decB > decA)
			totalDecPlace = decB;
		
		var toFixPlace = p;

		return insertDecPlace(finalResult, totalDecPlace, toFixPlace);
	};
	
	self.multiply = function(a, b, p) {
	
		purifyNumbers(a, b);			
		
		if (parseInt(intA) == 0 || parseInt(intB) == 0) {
			var res = '0.';
			var k;
			for(k = 0; k < parseInt(p); k++){
				res = res + '0';
			}
			
			return res;
		}

		intA = intA.split('').reverse();
		intB = intB.split('').reverse();
		var result = [];

		for (var i = 0; intA[i] >= 0; i++) {
			for (var j = 0; intB[j] >= 0; j++) {
				if (!result[i + j]) {
					result[i + j] = 0;
				}

				result[i + j] += intA[i] * intB[j];
			}
		}

		for (var i = 0; result[i] >= 0; i++) {
			if (result[i] >= 10) {
				if (!result[i + 1]) {
					result[i + 1] = 0;
				}

				result[i + 1] += parseInt(result[i] / 10);
				result[i] %= 10;
			}
		}

		var finalResult = result.reverse().join('');			
		
		var totalDecPlace = decA + decB;			

		var toFixPlace = p;

		return insertDecPlace(finalResult, totalDecPlace, toFixPlace);
	}
};
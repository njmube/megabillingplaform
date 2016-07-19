(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_emitterNewController', Free_emitterNewController);

    Free_emitterNewController.$inject = ['$scope', '$stateParams', '$q', 'entity', 'DataUtils', 'Free_emitter_file','Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function Free_emitterNewController ($scope, $stateParams, $q, entity, DataUtils, Free_emitter_file, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.free_emitter = entity;
        vm.type_taxpayers = Type_taxpayer.query();
        vm.accuracys = [2,3,4,5,6];
        vm.tax_regimes = Tax_regime.query({filtername:" "});
        vm.c_countrys = C_country.query({pg:1, filtername:" "});
        vm.c_states = C_state.query({countryId:151, filtername:" "});
        vm.c_municipalitys = null;
		vm.c_colonys = null;
        vm.messcertificate = null;
        vm.meskey = null;
        vm.messlogo = null;
        vm.edit = null;

        vm.onChangeC_country = onChangeC_country;
        vm.onChangeC_state = onChangeC_state;
        vm.onChangeC_municipality = onChangeC_municipality;
        vm.onChangeC_colony = onChangeC_colony;
        vm.clicEdit = clicEdit;

        vm.onChangeRFC = onChangeRFC;

        function clicEdit(){
            vm.edit = 'OK';
        }

		vm.load = function(id) {
			Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
            });
        };

        function onChangeC_country () {
			var countryId = vm.free_emitter.c_country.id;
            C_state.query({countryId: countryId, filtername:" "}, function(result){
                vm.c_states = result;
            });
        }

        function onChangeC_state () {
            var stateId = vm.free_emitter.c_state.id;
            C_municipality.query({stateId: stateId, filtername:" "}, function(result){
                vm.c_municipalitys = result;
            });
        }

        function onChangeC_municipality () {
            var municipalityId = vm.free_emitter.c_municipality.id;
            C_colony.query({municipalityId: municipalityId, filtername:" "}, function(result){
                vm.c_colonys = result;
            });
        }

        function onChangeC_colony(){
            C_zip_code.get({id : vm.free_emitter.c_colony.c_zip_code.id}, function(result) {
                vm.free_emitter.c_zip_code = result;
            });
        }

        function onChangeRFC(){
            if(vm.free_emitter.rfc.length == 12){
                vm.free_emitter.type_taxpayer = vm.type_taxpayers[0];
            }else
            {
                vm.free_emitter.type_taxpayer = vm.type_taxpayers[1];
            }
        }

		var onSaveSuccess = function (result) {
            vm.free_emitter =  result;
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

		vm.save = function () {
            vm.isSaving = true;
                if (vm.free_emitter.id !== null) {
					Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
                } else {
					Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
                }
        };

        vm.setPath_Certificate = function ($file, free_emitter) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        if($file.size <= 10485760 && $file.name.indexOf(".cer",0)+4 == $file.name.length){
                            vm.free_emitter.path_certificate = $file.name;
                            free_emitter.filecertificate = base64Data;
                            free_emitter.filecertificateContentType = "cer";
                            vm.messcertificate = null;
                        }
                        else{
                            vm.messcertificate = true;
                        }
                    });
                });
            }
        };
        vm.setPath_Key = function ($file, free_emitter) {
            if ($file) {
                vm.path_key_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        if($file.size <= 10485760 && $file.name.indexOf(".key",0)+4 == $file.name.length){
                            vm.free_emitter.path_key = $file.name;
                            free_emitter.filekey = base64Data;
                            free_emitter.filekeyContentType = "key";
                            vm.messkey = null;
                        }
                        else{
                            vm.messkey = true;
                        }
                    });
                });
            }
        };
        vm.setLogo = function ($file, free_emitter) {
            if ($file) {
                vm.logo_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {

                        if($file.size <= 10485760 && ($file.type == "image/png" || $file.type == "image/jpeg") ){
                            vm.free_emitter.path_logo = $file.name;
                            free_emitter.filelogo = base64Data;
                            free_emitter.filelogoContentType = $file.type;
                            vm.messlogo = null;
                        }
                        else{
                            vm.messlogo = true;
                        }
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

    }
})();

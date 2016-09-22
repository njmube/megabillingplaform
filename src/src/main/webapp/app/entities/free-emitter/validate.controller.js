(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ValidateController', ValidateController);

    ValidateController.$inject = ['$scope', '$stateParams', '$uibModal', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Free_emitter_file','Free_emitter', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function ValidateController ($scope, $stateParams, $uibModal, $uibModalInstance, $q, entity, DataUtils, Free_emitter_file, Free_emitter, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.free_emitter = entity;
        vm.showInfo = false;
        vm.messcertificate = null;
        vm.meskey = null;
        vm.edit = null;

        vm.onValidate = onValidate;

        vm.load = function(id) {
            Free_emitter.get({id : id}, function(result) {
                vm.free_emitter = result;
            });
        };

        function onValidate(){
            if(vm.free_emitter.pass_certificate != null && vm.free_emitter.pass_certificate != "" && vm.free_emitter.filekey!=null && vm.free_emitter.filecertificate!=null){
                vm.messvalidate = false;
                vm.free_emitter.info_certificate = 'validating...';
                Free_emitter.update(vm.free_emitter, onSaveSuccess, onSaveError);
            }
        }

        var onSaveSuccess = function (result) {
            vm.free_emitter =  result;
            if(vm.free_emitter.info_certificate != null){
            }else
            {
                vm.showInfo = false;
                $uibModalInstance.close({
                    free_emitter: vm.free_emitter
                });
            }
        };

        var onSaveError = function () {
            vm.isSaving = false;
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

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;


    }
})();


(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ValidateDCController', ValidateDCController);

    ValidateDCController.$inject = ['$scope', '$stateParams', '$uibModal', 'Taxpayer_account', '$uibModalInstance', '$q', 'entity', 'DataUtils', 'Tax_regime', 'C_country', 'C_state', 'C_municipality', 'C_colony', 'C_zip_code','Type_taxpayer'];

    function ValidateDCController ($scope, $stateParams, $uibModal, Taxpayer_account, $uibModalInstance, $q, entity, DataUtils, Tax_regime, C_country, C_state, C_municipality, C_colony, C_zip_code, Type_taxpayer) {
        var vm = this;

        vm.taxpayer_account = entity;
        vm.showInfo = false;
        vm.messcertificate = null;
        vm.meskey = null;
        vm.edit = null;
        vm.messpass=null;

        vm.onValidate = onValidate;

        vm.load = function(id) {
            Taxpayer_account.get({id : id}, function(result) {
                vm.taxpayer_account = result;
            });
        };

        function onValidate(){
            vm.messpass=null;
            if(vm.taxpayer_account.taxpayer_certificate.pass_certificate != null &&
                vm.taxpayer_account.taxpayer_certificate.pass_certificate != "" &&
                vm.taxpayer_account.taxpayer_certificate.filekey!=null &&
                vm.taxpayer_account.taxpayer_certificate.filecertificate!=null){

                vm.messvalidate = false;
                vm.taxpayer_account.taxpayer_certificate.info_certificate = 'validando...';
                Taxpayer_account.update(vm.taxpayer_account, onSaveSuccess, onSaveError);
            }
            if(vm.taxpayer_account.taxpayer_certificate.pass_certificate == null ||
                vm.taxpayer_account.taxpayer_certificate.pass_certificate == "") {
                vm.messpass='OK';
            }
        }

        var onSaveSuccess = function (result) {
            vm.taxpayer_account =  result;
            if(vm.taxpayer_account.taxpayer_certificate.info_certificate != null){
            }else
            {
                vm.showInfo = false;
                $uibModalInstance.close({
                    taxpayer_account: vm.taxpayer_account
                });
            }
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.setPath_Certificate = function ($file, taxpayer_account) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        if($file.size <= 10485760 && $file.name.indexOf(".cer",0)+4 == $file.name.length){
                            vm.taxpayer_account.taxpayer_certificate.path_certificate = $file.name;
                            taxpayer_account.taxpayer_certificate.filecertificate = base64Data;
                            taxpayer_account.taxpayer_certificate.filecertificateContentType = "cer";
                            vm.messcertificate = null;
                        }
                        else{
                            vm.messcertificate = true;
                        }
                    });
                });
            }
        };
        vm.setPath_Key = function ($file, taxpayer_account) {
            if ($file) {
                vm.path_key_file = $file;
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        if($file.size <= 10485760 && $file.name.indexOf(".key",0)+4 == $file.name.length){
                            vm.taxpayer_account.taxpayer_certificate.path_key = $file.name;
                            taxpayer_account.taxpayer_certificate.filekey = base64Data;
                            taxpayer_account.taxpayer_certificate.filekeyContentType = "key";
                            vm.messkey = null;
                        }
                        else{
                            vm.messkey = true;
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


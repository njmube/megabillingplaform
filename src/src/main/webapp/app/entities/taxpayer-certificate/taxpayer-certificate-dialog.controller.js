(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Taxpayer_certificateDialogController', Taxpayer_certificateDialogController);

    Taxpayer_certificateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Taxpayer_certificate'];

    function Taxpayer_certificateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Taxpayer_certificate) {
        var vm = this;

        vm.taxpayer_certificate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.taxpayer_certificate.id !== null) {
                Taxpayer_certificate.update(vm.taxpayer_certificate, onSaveSuccess, onSaveError);
            } else {
                Taxpayer_certificate.save(vm.taxpayer_certificate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:taxpayer_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setFilecertificate = function ($file, taxpayer_certificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        taxpayer_certificate.filecertificate = base64Data;
                        taxpayer_certificate.filecertificateContentType = $file.type;
                    });
                });
            }
        };

        vm.setFilekey = function ($file, taxpayer_certificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        taxpayer_certificate.filekey = base64Data;
                        taxpayer_certificate.filekeyContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.date_certificate = false;
        vm.datePickerOpenStatus.date_created_cert = false;
        vm.datePickerOpenStatus.date_expiration_cert = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

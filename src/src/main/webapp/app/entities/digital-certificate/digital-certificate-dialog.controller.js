(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Digital_certificateDialogController', Digital_certificateDialogController);

    Digital_certificateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Digital_certificate'];

    function Digital_certificateDialogController ($scope, $stateParams, $uibModalInstance, DataUtils, entity, Digital_certificate) {
        var vm = this;
        vm.digital_certificate = entity;
        vm.load = function(id) {
            Digital_certificate.get({id : id}, function(result) {
                vm.digital_certificate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:digital_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.digital_certificate.id !== null) {
                Digital_certificate.update(vm.digital_certificate, onSaveSuccess, onSaveError);
            } else {
                Digital_certificate.save(vm.digital_certificate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        vm.setAdrees = function ($file, digital_certificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        digital_certificate.adrees = base64Data;
                        digital_certificate.adreesContentType = $file.type;
                    });
                });
            }
        };

        vm.setPrivate_key = function ($file, digital_certificate) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        digital_certificate.private_key = base64Data;
                        digital_certificate.private_keyContentType = $file.type;
                    });
                });
            }
        };

        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
    }
})();

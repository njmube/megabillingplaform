(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Free_digital_certificateDialogController', Free_digital_certificateDialogController);

    Free_digital_certificateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Free_digital_certificate'];

    function Free_digital_certificateDialogController ($scope, $stateParams, $uibModalInstance, entity, Free_digital_certificate) {
        var vm = this;
        vm.free_digital_certificate = entity;
        vm.load = function(id) {
            Free_digital_certificate.get({id : id}, function(result) {
                vm.free_digital_certificate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:free_digital_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.free_digital_certificate.id !== null) {
                Free_digital_certificate.update(vm.free_digital_certificate, onSaveSuccess, onSaveError);
            } else {
                Free_digital_certificate.save(vm.free_digital_certificate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

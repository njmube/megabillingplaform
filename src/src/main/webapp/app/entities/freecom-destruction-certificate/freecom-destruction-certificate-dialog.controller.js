(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_destruction_certificateDialogController', Freecom_destruction_certificateDialogController);

    Freecom_destruction_certificateDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_destruction_certificate', 'C_class'];

    function Freecom_destruction_certificateDialogController ($scope, $stateParams, $uibModalInstance, entity, Freecom_destruction_certificate, C_class) {
        var vm = this;
        vm.freecom_destruction_certificate = entity;
        vm.c_classs = C_class.query();
        vm.load = function(id) {
            Freecom_destruction_certificate.get({id : id}, function(result) {
                vm.freecom_destruction_certificate = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:freecom_destruction_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.freecom_destruction_certificate.id !== null) {
                Freecom_destruction_certificate.update(vm.freecom_destruction_certificate, onSaveSuccess, onSaveError);
            } else {
                Freecom_destruction_certificate.save(vm.freecom_destruction_certificate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

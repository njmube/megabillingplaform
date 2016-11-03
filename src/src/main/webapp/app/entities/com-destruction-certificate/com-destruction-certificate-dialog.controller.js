(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_destruction_certificateDialogController', Com_destruction_certificateDialogController);

    Com_destruction_certificateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_destruction_certificate', 'Cfdi', 'C_type_series'];

    function Com_destruction_certificateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_destruction_certificate, Cfdi, C_type_series) {
        var vm = this;
        vm.com_destruction_certificate = entity;
        vm.cfdis = Cfdi.query();
        vm.c_type_series = C_type_series.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_destruction_certificateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_destruction_certificate.id !== null) {
                Com_destruction_certificate.update(vm.com_destruction_certificate, onSaveSuccess, onSaveError);
            } else {
                Com_destruction_certificate.save(vm.com_destruction_certificate, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

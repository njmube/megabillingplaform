(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_ecc_11DialogController', Com_ecc_11DialogController);

    Com_ecc_11DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_ecc_11', 'Cfdi'];

    function Com_ecc_11DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_ecc_11, Cfdi) {
        var vm = this;
        vm.com_ecc_11 = entity;
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_ecc_11Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_ecc_11.id !== null) {
                Com_ecc_11.update(vm.com_ecc_11, onSaveSuccess, onSaveError);
            } else {
                Com_ecc_11.save(vm.com_ecc_11, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

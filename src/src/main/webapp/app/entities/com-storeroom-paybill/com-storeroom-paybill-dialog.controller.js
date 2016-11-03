(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_storeroom_paybillDialogController', Com_storeroom_paybillDialogController);

    Com_storeroom_paybillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_storeroom_paybill', 'Cfdi'];

    function Com_storeroom_paybillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_storeroom_paybill, Cfdi) {
        var vm = this;
        vm.com_storeroom_paybill = entity;
        vm.cfdis = Cfdi.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_storeroom_paybillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_storeroom_paybill.id !== null) {
                Com_storeroom_paybill.update(vm.com_storeroom_paybill, onSaveSuccess, onSaveError);
            } else {
                Com_storeroom_paybill.save(vm.com_storeroom_paybill, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

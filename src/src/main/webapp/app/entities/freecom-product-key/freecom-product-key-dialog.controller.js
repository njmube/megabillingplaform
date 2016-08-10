(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_product_keyDialogController', Freecom_product_keyDialogController);

    Freecom_product_keyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Freecom_product_key'];

    function Freecom_product_keyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Freecom_product_key) {
        var vm = this;

        vm.freecom_product_key = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.freecom_product_key.id !== null) {
                Freecom_product_key.update(vm.freecom_product_key, onSaveSuccess, onSaveError);
            } else {
                Freecom_product_key.save(vm.freecom_product_key, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('megabillingplatformApp:freecom_product_keyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

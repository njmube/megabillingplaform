(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_product_keyDialogController', Com_product_keyDialogController);

    Com_product_keyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Com_product_key'];

    function Com_product_keyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Com_product_key) {
        var vm = this;
        vm.com_product_key = entity;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:com_product_keyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.com_product_key.id !== null) {
                Com_product_key.update(vm.com_product_key, onSaveSuccess, onSaveError);
            } else {
                Com_product_key.save(vm.com_product_key, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

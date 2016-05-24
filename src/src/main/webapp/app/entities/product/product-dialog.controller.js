(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ProductDialogController', ProductDialogController);

    ProductDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Product', 'Category_product'];

    function ProductDialogController ($scope, $stateParams, $uibModalInstance, entity, Product, Category_product) {
        var vm = this;
        vm.product = entity;
        vm.category_products = Category_product.query();
        vm.load = function(id) {
            Product.get({id : id}, function(result) {
                vm.product = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.product.id !== null) {
                Product.update(vm.product, onSaveSuccess, onSaveError);
            } else {
                Product.save(vm.product, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Category_productDialogController', Category_productDialogController);

    Category_productDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Category_product', 'Product'];

    function Category_productDialogController ($scope, $stateParams, $uibModalInstance, entity, Category_product, Product) {
        var vm = this;
        vm.category_product = entity;
        vm.products = Product.query();
        vm.load = function(id) {
            Category_product.get({id : id}, function(result) {
                vm.category_product = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('megabillingplatformApp:category_productUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.category_product.id !== null) {
                Category_product.update(vm.category_product, onSaveSuccess, onSaveError);
            } else {
                Category_product.save(vm.category_product, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();

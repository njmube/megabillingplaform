(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Category_productDeleteController',Category_productDeleteController);

    Category_productDeleteController.$inject = ['$uibModalInstance', 'entity', 'Category_product'];

    function Category_productDeleteController($uibModalInstance, entity, Category_product) {
        var vm = this;
        vm.category_product = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Category_product.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

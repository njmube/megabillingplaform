(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_product_keyDeleteController',Com_product_keyDeleteController);

    Com_product_keyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_product_key'];

    function Com_product_keyDeleteController($uibModalInstance, entity, Com_product_key) {
        var vm = this;
        vm.com_product_key = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_product_key.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

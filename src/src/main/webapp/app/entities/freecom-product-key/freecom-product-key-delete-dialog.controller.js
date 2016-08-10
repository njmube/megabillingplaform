(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_product_keyDeleteController',Freecom_product_keyDeleteController);

    Freecom_product_keyDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_product_key'];

    function Freecom_product_keyDeleteController($uibModalInstance, entity, Freecom_product_key) {
        var vm = this;

        vm.freecom_product_key = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Freecom_product_key.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

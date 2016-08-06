(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_tax_typeDeleteController',Freecom_tax_typeDeleteController);

    Freecom_tax_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_tax_type'];

    function Freecom_tax_typeDeleteController($uibModalInstance, entity, Freecom_tax_type) {
        var vm = this;
        vm.freecom_tax_type = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_tax_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

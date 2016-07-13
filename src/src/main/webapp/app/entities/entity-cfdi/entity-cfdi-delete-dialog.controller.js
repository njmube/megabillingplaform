(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Entity_cfdiDeleteController',Entity_cfdiDeleteController);

    Entity_cfdiDeleteController.$inject = ['$uibModalInstance', 'entity', 'Entity_cfdi'];

    function Entity_cfdiDeleteController($uibModalInstance, entity, Entity_cfdi) {
        var vm = this;
        vm.entity_cfdi = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Entity_cfdi.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_taxlegendsDeleteController',Com_taxlegendsDeleteController);

    Com_taxlegendsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_taxlegends'];

    function Com_taxlegendsDeleteController($uibModalInstance, entity, Com_taxlegends) {
        var vm = this;
        vm.com_taxlegends = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_taxlegends.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_taxlegendsDeleteController',Freecom_taxlegendsDeleteController);

    Freecom_taxlegendsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_taxlegends'];

    function Freecom_taxlegendsDeleteController($uibModalInstance, entity, Freecom_taxlegends) {
        var vm = this;
        vm.freecom_taxlegends = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_taxlegends.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

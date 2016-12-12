(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_dataunenajenanteDeleteController',Freecom_dataunenajenanteDeleteController);

    Freecom_dataunenajenanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Freecom_dataunenajenante'];

    function Freecom_dataunenajenanteDeleteController($uibModalInstance, entity, Freecom_dataunenajenante) {
        var vm = this;
        vm.freecom_dataunenajenante = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Freecom_dataunenajenante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

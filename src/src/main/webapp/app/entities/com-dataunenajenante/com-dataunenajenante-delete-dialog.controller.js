(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunenajenanteDeleteController',Com_dataunenajenanteDeleteController);

    Com_dataunenajenanteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_dataunenajenante'];

    function Com_dataunenajenanteDeleteController($uibModalInstance, entity, Com_dataunenajenante) {
        var vm = this;
        vm.com_dataunenajenante = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_dataunenajenante.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

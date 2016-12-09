(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_dataunacquiringDeleteController',Com_dataunacquiringDeleteController);

    Com_dataunacquiringDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_dataunacquiring'];

    function Com_dataunacquiringDeleteController($uibModalInstance, entity, Com_dataunacquiring) {
        var vm = this;
        vm.com_dataunacquiring = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_dataunacquiring.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

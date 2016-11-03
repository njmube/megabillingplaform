(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Com_determinedDeleteController',Com_determinedDeleteController);

    Com_determinedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Com_determined'];

    function Com_determinedDeleteController($uibModalInstance, entity, Com_determined) {
        var vm = this;
        vm.com_determined = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Com_determined.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

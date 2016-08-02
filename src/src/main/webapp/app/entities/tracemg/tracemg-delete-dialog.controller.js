(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('TracemgDeleteController',TracemgDeleteController);

    TracemgDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tracemg'];

    function TracemgDeleteController($uibModalInstance, entity, Tracemg) {
        var vm = this;
        vm.tracemg = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tracemg.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

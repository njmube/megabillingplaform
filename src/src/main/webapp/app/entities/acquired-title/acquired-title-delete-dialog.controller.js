(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Acquired_titleDeleteController',Acquired_titleDeleteController);

    Acquired_titleDeleteController.$inject = ['$uibModalInstance', 'entity', 'Acquired_title'];

    function Acquired_titleDeleteController($uibModalInstance, entity, Acquired_title) {
        var vm = this;
        vm.acquired_title = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Acquired_title.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

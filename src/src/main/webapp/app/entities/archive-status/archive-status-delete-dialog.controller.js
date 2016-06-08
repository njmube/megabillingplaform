(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Archive_statusDeleteController',Archive_statusDeleteController);

    Archive_statusDeleteController.$inject = ['$uibModalInstance', 'entity', 'Archive_status'];

    function Archive_statusDeleteController($uibModalInstance, entity, Archive_status) {
        var vm = this;
        vm.archive_status = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Archive_status.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();

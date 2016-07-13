(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_apawDetailController', Freecom_apawDetailController);

    Freecom_apawDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_apaw', 'Well_type', 'Acquired_title', 'Features_work_piece', 'Free_cfdi'];

    function Freecom_apawDetailController($scope, $rootScope, $stateParams, entity, Freecom_apaw, Well_type, Acquired_title, Features_work_piece, Free_cfdi) {
        var vm = this;
        vm.freecom_apaw = entity;
        vm.load = function (id) {
            Freecom_apaw.get({id: id}, function(result) {
                vm.freecom_apaw = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_apawUpdate', function(event, result) {
            vm.freecom_apaw = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

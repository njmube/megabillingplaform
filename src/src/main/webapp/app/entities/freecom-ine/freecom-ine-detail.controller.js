(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('Freecom_ineDetailController', Freecom_ineDetailController);

    Freecom_ineDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Freecom_ine', 'Free_cfdi'];

    function Freecom_ineDetailController($scope, $rootScope, $stateParams, entity, Freecom_ine, Free_cfdi) {
        var vm = this;
        vm.freecom_ine = entity;
        vm.load = function (id) {
            Freecom_ine.get({id: id}, function(result) {
                vm.freecom_ine = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:freecom_ineUpdate', function(event, result) {
            vm.freecom_ine = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

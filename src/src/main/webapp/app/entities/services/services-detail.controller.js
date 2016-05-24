(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('ServicesDetailController', ServicesDetailController);

    ServicesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Services'];

    function ServicesDetailController($scope, $rootScope, $stateParams, entity, Services) {
        var vm = this;
        vm.services = entity;
        vm.load = function (id) {
            Services.get({id: id}, function(result) {
                vm.services = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:servicesUpdate', function(event, result) {
            vm.services = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

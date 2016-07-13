(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .controller('CommitteeDetailController', CommitteeDetailController);

    CommitteeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Committee'];

    function CommitteeDetailController($scope, $rootScope, $stateParams, entity, Committee) {
        var vm = this;
        vm.committee = entity;
        vm.load = function (id) {
            Committee.get({id: id}, function(result) {
                vm.committee = result;
            });
        };
        var unsubscribe = $rootScope.$on('megabillingplatformApp:committeeUpdate', function(event, result) {
            vm.committee = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();

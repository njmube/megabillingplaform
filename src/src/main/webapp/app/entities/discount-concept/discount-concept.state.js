(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('discount-concept', {
            parent: 'entity',
            url: '/discount-concept',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.discount_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discount-concept/discount-concepts.html',
                    controller: 'Discount_conceptController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discount_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('discount-concept-detail', {
            parent: 'entity',
            url: '/discount-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.discount_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/discount-concept/discount-concept-detail.html',
                    controller: 'Discount_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('discount_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Discount_concept', function($stateParams, Discount_concept) {
                    return Discount_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('discount-concept.new', {
            parent: 'discount-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discount-concept/discount-concept-dialog.html',
                    controller: 'Discount_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('discount-concept', null, { reload: true });
                }, function() {
                    $state.go('discount-concept');
                });
            }]
        })
        .state('discount-concept.edit', {
            parent: 'discount-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discount-concept/discount-concept-dialog.html',
                    controller: 'Discount_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Discount_concept', function(Discount_concept) {
                            return Discount_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('discount-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('discount-concept.delete', {
            parent: 'discount-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/discount-concept/discount-concept-delete-dialog.html',
                    controller: 'Discount_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Discount_concept', function(Discount_concept) {
                            return Discount_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('discount-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
